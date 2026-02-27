import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState, useMemo } from "react";
import axios from "axios";
import { ChevronLeft, Save } from 'lucide-react';
import { Paging } from "../../common/Paging"; // Paging 컴포넌트 import

const AdminChaFLLiEd = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const isNew = id === 'new';

    // 폼 데이터 상태
    const [chaFLLiEd, setChaFLLiEd] = useState({
        charImage: '',
        charName: '',
        content: '',
        userNickname: '',
        createAt: '',
        active: 'waiting'
    });

    // 리스트 및 페이징 상태
    const [chaFLs, setChaFLs] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(5); // 수정 페이지이므로 리스트는 조금만 보여줌

    useEffect(() => {
        const loadData = async () => {
            try {
                const response = await axios.get('/api/AdminChaFL');
                const data = Array.isArray(response.data) ? response.data : [];
                setChaFLs(data);

                if (!isNew) {
                    const target = data.find(item => item.id === Number(id));
                    if (target) {
                        setChaFLLiEd(target);
                    }
                }
            } catch (e) {
                console.error("데이터 로딩 실패:", e);
            }
        };
        loadData();
    }, [id, isNew]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setChaFLLiEd(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        try {
            if (isNew) {
                await axios.post('/api/AdminChaFL', chaFLLiEd);
                alert('새 명대사가 추가되었습니다.');
            } else {
                await axios.put(`/api/AdminChaFL/${id}`, chaFLLiEd);
                alert('명대사가 수정되었습니다.');
            }
            navigate('/AdminChaFL');
        } catch (error) {
            console.error("저장 실패:", error);
            alert("저장에 실패했습니다.");
        }
    };

    // 페이징 로직
    const currentItems = useMemo(() => {
        const indexOfLastItem = currentPage * itemsPerPage;
        const indexOfFirstItem = indexOfLastItem - itemsPerPage;
        return chaFLs.slice(indexOfFirstItem, indexOfLastItem);
    }, [chaFLs, currentPage, itemsPerPage]);

    const totalPages = Math.ceil(chaFLs.length / itemsPerPage);

    const handleRowClick = (itemId) => {
        navigate(`/AdminChaFLLiEd/${itemId}`);
    };

    return (
        <div className="min-h-screen bg-slate-50 p-8">
            <div className="max-w-4xl mx-auto">
                <button onClick={() => navigate('/AdminChaFL')} className="flex items-center gap-1 text-slate-500 hover:text-primary font-bold mb-8 transition-colors">
                    <ChevronLeft size={20} />
                    Back to List
                </button>

                <div className="bg-white rounded-2xl shadow-sm border border-slate-100 p-8 mb-8">
                    <h1 className="text-2xl font-black text-slate-800 mb-8">
                        {isNew ? 'Add New Famous Line' : 'Edit Famous Line'}
                    </h1>
                    
                    <form onSubmit={handleSubmit} className="space-y-6">
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div>
                                <label htmlFor="charName" className="block text-sm font-bold text-slate-600 mb-1">Character Name</label>
                                <input
                                    type="text"
                                    id="charName"
                                    name="charName"
                                    value={chaFLLiEd.charName || ''}
                                    onChange={handleChange}
                                    className="w-full px-4 py-2 rounded-lg bg-slate-100 border-transparent focus:outline-none focus:ring-2 focus:ring-primary"
                                />
                            </div>
                            <div>
                                <label htmlFor="userNickname" className="block text-sm font-bold text-slate-600 mb-1">User Nickname</label>
                                <input
                                    type="text"
                                    id="userNickname"
                                    name="userNickname"
                                    value={chaFLLiEd.userNickname || ''}
                                    onChange={handleChange}
                                    className="w-full px-4 py-2 rounded-lg bg-slate-100 border-transparent focus:outline-none focus:ring-2 focus:ring-primary"
                                />
                            </div>
                        </div>
                        
                        <div>
                            <label htmlFor="content" className="block text-sm font-bold text-slate-600 mb-1">Content</label>
                            <textarea
                                id="content"
                                name="content"
                                value={chaFLLiEd.content || ''}
                                onChange={handleChange}
                                rows={4}
                                className="w-full px-4 py-2 rounded-lg bg-slate-100 border-transparent focus:outline-none focus:ring-2 focus:ring-primary"
                            />
                        </div>
                        <div>
                            <label htmlFor="charImage" className="block text-sm font-bold text-slate-600 mb-1">Image URL</label>
                            <input
                                type="text"
                                id="charImage"
                                name="charImage"
                                value={chaFLLiEd.charImage || ''}
                                onChange={handleChange}
                                className="w-full px-4 py-2 rounded-lg bg-slate-100 border-transparent focus:outline-none focus:ring-2 focus:ring-primary"
                            />
                        </div>
                        
                        <div className="flex justify-end pt-4">
                            <button type="submit" className="flex items-center gap-2 px-6 py-2.5 rounded-lg bg-primary text-white font-bold shadow hover:shadow-lg hover:-translate-y-0.5 transition-all">
                                <Save size={18} />
                                {isNew ? 'Save' : 'Save Changes'}
                            </button>
                        </div>
                    </form>
                </div>

                {/* 하단 리스트 및 페이징 */}
                <div className="bg-white rounded-2xl shadow-sm border border-slate-100 overflow-hidden">
                    <div className="p-5 border-b border-slate-100">
                        <h2 className="text-lg font-bold text-slate-800">Other Requests</h2>
                    </div>
                    <div className="grid grid-cols-12 gap-4 p-4 bg-slate-50 text-xs font-bold text-slate-500 uppercase tracking-wider text-center">
                        <div className="col-span-1">ID</div>
                        <div className="col-span-3 text-left">Character</div>
                        <div className="col-span-5 text-left">Content</div>
                        <div className="col-span-3">Status</div>
                    </div>
                    <ul className="divide-y divide-slate-100">
                        {currentItems.map((item) => (
                            <li 
                                key={item.id} 
                                onClick={() => handleRowClick(item.id)}
                                className={`grid grid-cols-12 gap-4 p-4 items-center hover:bg-slate-50 cursor-pointer transition-colors text-sm ${item.id === Number(id) ? 'bg-blue-50' : ''}`}
                            >
                                <div className="col-span-1 text-center text-slate-500">{item.id}</div>
                                <div className="col-span-3 font-bold text-slate-700 truncate">{item.charName}</div>
                                <div className="col-span-5 text-slate-600 truncate">{item.content}</div>
                                <div className="col-span-3 text-center">
                                    <span className={`px-2 py-1 rounded-full text-xs font-bold ${
                                        item.active === 'accept' ? 'bg-green-100 text-green-600' :
                                        item.active === 'reject' ? 'bg-red-100 text-red-600' :
                                        'bg-orange-100 text-orange-600'
                                    }`}>
                                        {item.active}
                                    </span>
                                </div>
                            </li>
                        ))}
                    </ul>
                    
                    {/* 페이지네이션 컴포넌트 사용 */}
                    <div className="flex justify-center p-4 border-t border-slate-100">
                        <Paging page={currentPage} totalPage={totalPages} setPage={setCurrentPage} />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AdminChaFLLiEd;
