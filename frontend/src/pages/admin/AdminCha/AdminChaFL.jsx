import { useState, useEffect, useMemo } from 'react';
import axios from 'axios';
import { AdminChaFLLi } from "../../../components/admin/AdminCha";
import { Quote, Search } from 'lucide-react';
import { Paging } from "../../../components/common/Paging"; // Paging 컴포넌트 import

const AdminChaFL = () => {
    const [chaFLs, setChaFLs] = useState([]);
    const [filteredChaFLs, setFilteredChaFLs] = useState([]);
    
    // Filter & Pagination States
    const [searchTerm, setSearchTerm] = useState('');
    const [statusFilter, setStatusFilter] = useState('all');
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);

    const loadData = async () => {
        try {
            const response = await axios.get('/api/AdminChaFL');
            const data = Array.isArray(response.data) ? response.data : [];
            setChaFLs(data);
            setFilteredChaFLs(data);
        } catch (e) {
            console.error("데이터 로딩 실패:", e);
            setChaFLs([]);
            setFilteredChaFLs([]);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    // 필터링 로직
    useEffect(() => {
        let result = [...chaFLs];

        if (searchTerm) {
            result = result.filter(item =>
                (item.charName && item.charName.toLowerCase().includes(searchTerm.toLowerCase())) ||
                (item.content && item.content.toLowerCase().includes(searchTerm.toLowerCase())) ||
                (item.userNickname && item.userNickname.toLowerCase().includes(searchTerm.toLowerCase()))
            );
        }

        if (statusFilter !== 'all') {
            result = result.filter(item => item.active === statusFilter);
        }

        setFilteredChaFLs(result);
        setCurrentPage(1);
    }, [chaFLs, searchTerm, statusFilter]);

    // 페이지네이션 로직
    const currentItems = useMemo(() => {
        const indexOfLastItem = currentPage * itemsPerPage;
        const indexOfFirstItem = indexOfLastItem - itemsPerPage;
        return filteredChaFLs.slice(indexOfFirstItem, indexOfLastItem);
    }, [filteredChaFLs, currentPage, itemsPerPage]);

    const totalPages = Math.ceil(filteredChaFLs.length / itemsPerPage);

    // 액션 핸들러
    const handleApprove = async (id) => {
        if (confirm('이 명대사 신청을 승인하시겠습니까?')) {
            try {
                await axios.patch(`/api/AdminChaFL/${id}/approve`);
                alert("승인되었습니다.");
                loadData();
            } catch (e) {
                alert("승인 실패");
            }
        }
    };

    const handleReject = async (id) => {
        if (confirm('이 명대사 신청을 거절하시겠습니까?')) {
            try {
                await axios.patch(`/api/AdminChaFL/${id}/reject`);
                alert("거절 처리되었습니다.");
                loadData();
            } catch (e) {
                alert("거절 실패");
            }
        }
    };

    const handleDelete = async (id) => {
        if (confirm('등록된 명대사를 영구 삭제하시겠습니까?')) {
            try {
                await axios.delete(`/api/AdminChaFL/${id}`);
                alert("삭제되었습니다.");
                loadData();
            } catch (e) {
                alert("삭제 실패");
            }
        }
    };

    return (
        <div className="min-h-screen bg-slate-50 p-8">
            <div className="max-w-7xl mx-auto">
                <div className="flex items-center gap-4 mb-10">
                    <div className="w-1.5 h-10 bg-primary rounded-full"></div>
                    <div>
                        <h2 className="text-3xl font-black text-slate-800 tracking-tight flex items-center gap-2">
                            Famous Line Requests
                            <Quote className="text-primary" size={28} />
                        </h2>
                        <p className="text-sm font-medium text-slate-400 tracking-wide uppercase">캐릭터 명대사 신청 관리</p>
                    </div>
                </div>

                {/* 필터 및 검색 영역 */}
                <div className="flex flex-col md:flex-row justify-between items-center gap-4 mb-8">
                    <div className="relative w-full md:w-80">
                        <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400" size={18} />
                        <input 
                            type="text" 
                            placeholder="제목, 내용, 유저 검색..." 
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
                    <div className="grid grid-cols-12 gap-4 p-5 bg-slate-100/80 text-sm font-bold text-slate-500 uppercase tracking-wider text-left">
                        <div className="col-span-1 text-center">Image</div>
                        <div className="col-span-3">Character Name</div>
                        <div className="col-span-4">Content</div>
                        <div className="col-span-1 text-center">User</div>
                        <div className="col-span-1 text-center">Date</div>
                        <div className="col-span-2 text-center">Status / Actions</div>
                    </div>

                    <ul className="divide-y divide-slate-100">
                        {currentItems.length > 0 ? (
                            currentItems.map(chaFl => (
                                <AdminChaFLLi
                                    key={chaFl.id}
                                    chaFl={chaFl}
                                    onApprove={() => handleApprove(chaFl.id)}
                                    onReject={() => handleReject(chaFl.id)}
                                    onDelete={() => handleDelete(chaFl.id)}
                                />
                            ))
                        ) : (
                            <div className="p-10 text-center text-slate-400">
                                {searchTerm ? "검색 결과가 없습니다." : "데이터가 없습니다."}
                            </div>
                        )}
                    </ul>
                </div>

                {/* 페이지네이션 컴포넌트 사용 */}
                <div className="flex justify-center mt-8">
                    <Paging page={currentPage} totalPage={totalPages} setPage={setCurrentPage} />
                </div>
            </div>
        </div>
    );
};

export default AdminChaFL;
