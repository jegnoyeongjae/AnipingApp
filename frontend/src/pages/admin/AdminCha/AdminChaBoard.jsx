import { useState, useEffect } from 'react';
import axios from 'axios';
import { Search, UserPlus, CheckCircle, XCircle, Trash2 } from 'lucide-react';
import { Paging } from "../../../components/common/Paging";

const AdminChaBoard = () => {
    const [requestsPage, setRequestsPage] = useState(null);
    
    // Filter & Pagination States
    const [searchTerm, setSearchTerm] = useState('');
    const [statusFilter, setStatusFilter] = useState('all');
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);

    const loadData = async (page, size, keyword, status) => {
        try {
            const params = {
                page: page - 1, // 백엔드는 0부터 시작
                size: size,
                keyword: keyword,
                status: status === 'all' ? null : status
            };
            const response = await axios.get('/api/AdminChaBoard', { params });
            setRequestsPage(response.data);
        } catch (e) {
            console.error("데이터 로딩 실패:", e);
        }
    };

    useEffect(() => {
        loadData(currentPage, itemsPerPage, searchTerm, statusFilter);
    }, [currentPage, itemsPerPage, statusFilter]); // searchTerm은 검색 버튼이나 엔터 칠 때 처리하는 게 좋지만, 여기선 입력 시 바로 반영하려면 추가

    // 검색 핸들러 (엔터 키 또는 검색 버튼 클릭 시)
    const handleSearch = (e) => {
        if (e.key === 'Enter' || e.type === 'click') {
            setCurrentPage(1);
            loadData(1, itemsPerPage, searchTerm, statusFilter);
        }
    };

    const handleApprove = async (id) => {
        if (confirm('이 캐릭터 신청을 승인하시겠습니까?')) {
            try {
                await axios.patch(`/api/AdminChaBoard/${id}/approve`);
                alert("승인되었습니다.");
                loadData(currentPage, itemsPerPage, searchTerm, statusFilter);
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
                loadData(currentPage, itemsPerPage, searchTerm, statusFilter);
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
                loadData(currentPage, itemsPerPage, searchTerm, statusFilter);
            } catch (e) {
                alert("삭제 실패");
            }
        }
    };

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
                            placeholder="캐릭터명, 애니명, 유저명 검색..." 
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            onKeyDown={handleSearch}
                            className="w-full pl-12 pr-4 py-3 rounded-xl bg-white border border-slate-200 focus:outline-none focus:border-primary focus:ring-2 focus:ring-primary/20 transition-all font-medium text-sm"
                        />
                    </div>

                    <div className="flex gap-3 w-full md:w-auto">
                        <select 
                            value={statusFilter} 
                            onChange={(e) => {
                                setStatusFilter(e.target.value);
                                setCurrentPage(1);
                            }}
                            className="px-4 py-3 rounded-xl bg-white border border-slate-200 text-sm font-bold text-slate-600 focus:outline-none focus:border-primary cursor-pointer"
                        >
                            <option value="all">모든 상태</option>
                            <option value="accept">등록완료</option>
                            <option value="waiting">신청대기</option>
                            <option value="reject">거절됨</option>
                        </select>

                        <select 
                            value={itemsPerPage} 
                            onChange={(e) => {
                                setItemsPerPage(Number(e.target.value));
                                setCurrentPage(1);
                            }}
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
                        {requestsPage && requestsPage.content && requestsPage.content.map((req, idx) => (
                            <li key={req.id} className="grid grid-cols-12 gap-4 p-5 items-center hover:bg-slate-50/50 transition-colors text-center">
                                <div className="col-span-1 text-slate-500 font-medium">
                                    {requestsPage.totalElements - ((currentPage - 1) * itemsPerPage + idx)}
                                </div>
                                <div className="col-span-3 text-left pl-4 font-bold text-slate-800">{req.name}</div>
                                <div className="col-span-3 text-left text-slate-600">{req.animeTitle ? `${req.animeTitle}` : '-'}</div>
                                <div className="col-span-2 text-slate-500 text-sm">{req.nickname ? `${req.nickname}` : '-'}</div>
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
                                        <button onClick={() => handleApprove(req.id)} className="p-2 text-slate-400 hover:bg-green-100 hover:text-green-600 rounded-full transition-all" title="승인">
                                            <CheckCircle size={16} />
                                        </button>
                                    )}
                                    
                                    <button onClick={() => handleDelete(req.id)} className="p-2 text-slate-400 hover:bg-red-100 hover:text-red-500 rounded-full transition-all" title="삭제">
                                        <Trash2 size={16} />
                                    </button>
                                </div>
                            </li>
                        ))}
                    </ul>
                    
                    {(!requestsPage || !requestsPage.content || requestsPage.content.length === 0) && (
                        <div className="p-10 text-center text-slate-400">
                            데이터가 없습니다.
                        </div>
                    )}
                </div>

                {/* 페이지네이션 컴포넌트 사용 */}
                {requestsPage && requestsPage.totalPages > 0 && (
                    <div className="flex justify-center mt-8">
                        <Paging page={currentPage} totalPage={requestsPage.totalPages} setPage={handlePageChange} pageCount={10} />
                    </div>
                )}
            </div>
        </div>
    );
};

export default AdminChaBoard;
