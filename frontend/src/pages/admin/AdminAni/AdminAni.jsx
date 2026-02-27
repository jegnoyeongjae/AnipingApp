import { useState, useEffect } from "react";
import axios from "axios";
import AdminAniLi from "../../../components/admin/AdminAni/AdminAniLi"; // 경로 수정 필요할 수 있음
import { Clapperboard, PlusCircle, ChevronLeft, ChevronRight } from 'lucide-react';
import { Link } from "react-router-dom";

const AdminAni = () => {
    const [anis, setAnis] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        loadData(page);
    }, [page]);

    const loadData = async (pageNumber) => {
        try {
            // 백엔드에서 Page<AdAniDto>를 반환하므로, content 필드에 리스트가 있음
            const response = await axios.get(`/api/AdminAni?page=${pageNumber}&size=10`);
            setAnis(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (e) {
            console.error("데이터 로드 실패:", e);
        }
    };

    const handleDeleteAni = async (id) => {
        if (!window.confirm("정말로 삭제하시겠습니까?")) return;

        try {
            await axios.delete(`/api/AdminAni/${id}`);
            // 삭제 후 현재 페이지 다시 로드
            loadData(page);
        } catch (e) {
            alert("삭제에 실패했습니다.");
            console.error(e);
        }
    };

    const handlePageChange = (newPage) => {
        if (newPage >= 0 && newPage < totalPages) {
            setPage(newPage);
        }
    };

    return (
        <div className="min-h-screen bg-slate-50 p-8">
            <div className="max-w-7xl mx-auto">
                <div className="flex justify-between items-end mb-10">
                    <div className="flex items-center gap-4">
                        <div className="w-1.5 h-10 bg-primary rounded-full"></div>
                        <div>
                            <h2 className="text-3xl font-black text-slate-800 tracking-tight flex items-center gap-2">
                                Animation Management
                                <Clapperboard className="text-primary" size={28} />
                            </h2>
                            <p className="text-sm font-medium text-slate-400 tracking-wide uppercase">애니메이션 목록 관리</p>
                        </div>
                    </div>
                    <Link to="/AdminAni/edit/new" className="flex items-center gap-2 bg-primary text-white px-5 py-2.5 rounded-lg font-bold shadow hover:shadow-lg hover:-translate-y-0.5 transition-all">
                        <PlusCircle size={18} />
                        <span>신규 등록</span>
                    </Link>
                </div>

                <div className="bg-white rounded-2xl shadow-sm border border-slate-100 overflow-hidden">
                    <div className="grid grid-cols-12 gap-4 p-5 bg-slate-100/80 text-sm font-bold text-slate-500 uppercase tracking-wider text-left">
                        <div className="col-span-1 text-center">ID</div>
                        <div className="col-span-4">Title</div>
                        <div className="col-span-2">Category</div>
                        <div className="col-span-2">Director</div>
                        <div className="col-span-1 text-center">ViewCount</div>
                        <div className="col-span-2 text-center">Actions</div>
                    </div>
                    <ul className="divide-y divide-slate-100">
                        {anis && anis.map((ani, idx)=>
                            <AdminAniLi
                                idx={(page * 10) + idx + 1}
                                ani={ani}
                                key={ani.id}
                                onDelete={() => handleDeleteAni(ani.id)}
                            />
                        )}
                    </ul>
                    
                    {/* 페이지네이션 컨트롤 */}
                    <div className="flex justify-center items-center p-4 gap-4">
                        <button 
                            onClick={() => handlePageChange(page - 1)} 
                            disabled={page === 0}
                            className="p-2 rounded-full hover:bg-slate-100 disabled:opacity-50 disabled:cursor-not-allowed"
                        >
                            <ChevronLeft size={20} />
                        </button>
                        <span className="text-sm font-bold text-slate-600">
                            Page {page + 1} of {totalPages}
                        </span>
                        <button 
                            onClick={() => handlePageChange(page + 1)} 
                            disabled={page === totalPages - 1}
                            className="p-2 rounded-full hover:bg-slate-100 disabled:opacity-50 disabled:cursor-not-allowed"
                        >
                            <ChevronRight size={20} />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AdminAni;
