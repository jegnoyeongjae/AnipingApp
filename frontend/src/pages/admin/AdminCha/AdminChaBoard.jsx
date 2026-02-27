import { useState, useEffect } from 'react';
import axios from 'axios';
import { Search, UserPlus, CheckCircle, XCircle, Trash2, ChevronLeft, ChevronRight } from 'lucide-react';

const AdminChaBoard = () => {
    const [requests, setRequests] = useState([]);
    const [filteredRequests, setFilteredRequests] = useState([]);
    
    // Filter & Pagination States
    const [searchTerm, setSearchTerm] = useState('');
    const [statusFilter, setStatusFilter] = useState('all'); // 상태 필터 추가
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);

    const loadData = async () => {
        try {
            const response = await axios.get('/api/AdminChaBoard');
            setRequests(response.data);
            setFilteredRequests(response.data);
        } catch (e) {
            console.error("데이터 로딩 실패:", e);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    useEffect(() => {
        let result = requests;

        if (searchTerm) {
            result = result.filter(req =>
                req.name.toLowerCase().includes(searchTerm.toLowerCase())
            );
        }

        if (statusFilter !== 'all') {
            result = result.filter(req => req.active === statusFilter);
        }

        setFilteredRequests(result);
        setCurrentPage(1);
    }, [requests, searchTerm, statusFilter]);

    const handleApprove = async (id) => {
        if (confirm('이 캐릭터 신청을 승인하시겠습니까?')) {
            try {
                await axios.patch(`/api/AdminChaBoard/${id}/approve`);
                alert("승인되었습니다.");
                loadData();
            } catch (e) {
                alert("승인 실패");
            }
        }
    };

    const handleReject = async (id) => {
        if (confirm('이 캐릭터 신청을 거절하시겠습니까?')) {
            try {
                await axios.patch(`/api/AdminChaBoard/${id}/reject`);

                alert("거절 처리되었습니다.");
                loadData();
            } catch (e) {
                console.error("거절 실패:", e);
                alert("거절 처리 중 오류가 발생했습니다.");
            }
        }
    };

    const handleDelete = async (id) => {
        if (confirm('정말 삭제하시겠습니까?')) {
            try {
                await axios.delete(`/api/AdminChaBoard/${id}`);
                alert("삭제되었습니다.");
                loadData();
            } catch (e) {
                alert("삭제 실패");
            }
        }
    };

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;

    const currentItems = Array.isArray(filteredRequests)
        ? filteredRequests.slice(indexOfFirstItem, indexOfLastItem)
        : [];

    const totalPages = Math.ceil(filteredRequests.length / itemsPerPage);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    return (
        <div className="min-h-screen bg-slate-50 p-8">
            <div className="max-w-7xl mx-auto">
                <div className="flex items-center gap-4 mb-10">
                    <div className="w-1.5 h-10 bg-primary rounded-full"></div>
                    <div>
                        <h2 className="text-3xl font-black text-slate-800 tracking-tight flex items-center gap-2">
                            Character Requests
                            <UserPlus className="text-primary" size={28} />
                        </h2>
                        <p className="text-sm font-medium text-slate-400 tracking-wide uppercase">캐릭터 게시판 신청 관리</p>
                    </div>
                </div>

                {/* 필터 및 검색 영역 */}
                <div className="flex flex-col md:flex-row justify-between items-center gap-4 mb-8">
                    <div className="relative w-full md:w-80">
                        <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400" size={18} />
                        <input 
                            type="text" 
                            placeholder="캐릭터명 또는 애니명 검색..." 
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="w-full pl-12 pr-4 py-3 rounded-xl bg-white border border-slate-200 focus:outline-none focus:border-primary focus:ring-2 focus:ring-primary/20 transition-all font-medium text-sm"
                        />
                    </div>

                    <div className="flex gap-3 w-full md:w-auto">
                        <select 
                            value={statusFilter} 
                            onChange={(e) => setStatusFilter(e.target.value)}
                            className="px-4 py-3 rounded-xl bg-white border border-slate-200 text-sm font-bold text-slate-600 focus:outline-none focus:border-primary cursor-pointer"
                        >
                            <option value="all">모든 상태</option>
                            <option value="accept">등록완료</option>
                            <option value="waiting">신청대기</option>
                            <option value="reject">거절됨</option>
                        </select>

                        <select 
                            value={itemsPerPage} 
                            onChange={(e) => setItemsPerPage(Number(e.target.value))}
                            className="px-4 py-3 rounded-xl bg-white border border-slate-200 text-sm font-bold text-slate-600 focus:outline-none focus:border-primary cursor-pointer"
                        >
                            <option value={10}>10개씩 보기</option>
                            <option value={15}>15개씩 보기</option>
                            <option value={20}>20개씩 보기</option>
                            <option value={30}>30개씩 보기</option>
                        </select>
                    </div>
                </div>

                <div className="bg-white rounded-2xl shadow-sm border border-slate-100 overflow-hidden">
                    <div className="grid grid-cols-12 gap-4 p-5 bg-slate-100/80 text-sm font-bold text-slate-500 uppercase tracking-wider text-center">
                        <div className="col-span-1">No</div>
                        <div className="col-span-3 text-left pl-4">Character</div>
                        <div className="col-span-3 text-left">Anime</div>
                        <div className="col-span-2">User</div>
                        <div className="col-span-1">Date</div>
                        <div className="col-span-2">Status / Actions</div>
                    </div>

                    <ul className="divide-y divide-slate-100">
                        {currentItems.map((req, idx) => (
                            <li key={req.id} className="grid grid-cols-12 gap-4 p-5 items-center hover:bg-slate-50/50 transition-colors text-center">
                                <div className="col-span-1 text-slate-500 font-medium">{idx + 1}</div>
                                <div className="col-span-3 text-left pl-4 font-bold text-slate-800">{req.name}</div>
                                <div className="col-span-3 text-left text-slate-600">{req.animeTitle ? `${req.animeTitle}` : '시스템입력'}</div>
                                <div className="col-span-2 text-slate-500 text-sm">{req.nickname ? `${req.nickname}` : '시스템입력'}</div>
                                <div className="col-span-1 text-slate-400 text-sm">{req.createAt ? req.createAt.split('T')[0] : '-'}</div>
                                <div className="col-span-2 flex items-center justify-center gap-2">
                                    {req.active === 'accept' && (
                                        <span className="flex items-center gap-1 text-xs font-bold text-green-600 bg-green-100 px-2.5 py-1 rounded-full">
                                            <CheckCircle size={12} /> 등록완료
                                        </span>
                                    )}

                                    {req.active === 'reject' && (
                                        <span className="flex items-center gap-1 text-xs font-bold text-red-600 bg-red-100 px-2.5 py-1 rounded-full">
                                            <XCircle size={12} /> 거절됨
                                        </span>
                                    )}

                                    {req.active === 'waiting' && (
                                        <button onClick={() => handleApprove(req.id)} className="...">
                                            <CheckCircle size={16} />
                                        </button>
                                    )}

                                </div>
                            </li>
                        ))}
                    </ul>
                    
                    {currentItems.length === 0 && (
                        <div className="p-10 text-center text-slate-400">
                            데이터가 없습니다.
                        </div>
                    )}
                </div>

                {/* 페이지네이션 */}
                {totalPages > 1 && (
                    <div className="flex justify-center gap-2 mt-8">
                        <button 
                            onClick={() => handlePageChange(currentPage - 1)}
                            disabled={currentPage === 1}
                            className="p-2 rounded-lg border border-slate-200 text-slate-500 hover:bg-slate-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                        >
                            <ChevronLeft size={20} />
                        </button>
                        
                        {Array.from({ length: totalPages }, (_, i) => i + 1).map(page => (
                            <button
                                key={page}
                                onClick={() => handlePageChange(page)}
                                className={`w-10 h-10 rounded-lg font-bold text-sm transition-all
                                    ${currentPage === page 
                                    ? 'bg-primary text-white shadow-md scale-105' 
                                    : 'bg-white border border-slate-200 text-slate-600 hover:bg-slate-50'}`}
                            >
                                {page}
                            </button>
                        ))}

                        <button 
                            onClick={() => handlePageChange(currentPage + 1)}
                            disabled={currentPage === totalPages}
                            className="p-2 rounded-lg border border-slate-200 text-slate-500 hover:bg-slate-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                        >
                            <ChevronRight size={20} />
                        </button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default AdminChaBoard;
