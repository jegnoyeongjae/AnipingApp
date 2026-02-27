import { useState, useEffect, useRef } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { Save, ArrowLeft, Upload, X, ImageIcon } from 'lucide-react';
import AdminAniCha from './AdminAniCha';

const AdminAniEdit = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const isEditing = id !== 'new';
    const fileInputRef = useRef(null);

    const [formData, setFormData] = useState({
        title: '',
        director: '',
        studio: '',
        description: '',
        date: '',
        grade: 'all',
        aniPv: '',
        cateId: '',
    });

    const [categories, setCategories] = useState([]);
    const [mainImage, setMainImage] = useState(null);
    const [previewUrl, setPreviewUrl] = useState(null);
    const [characters, setCharacters] = useState([]); // 캐릭터 상태를 여기서 관리
    const [deletedCharIds, setDeletedCharIds] = useState([]);

    useEffect(() => {
        axios.get('/api/AdminAni/tag')
        .then(res => {
            console.log("카테고리 로드 성공:", res.data);
            setCategories(res.data);
        })
        .catch(err => {
            console.error("카테고리 목록 로딩 실패:", err);
            setCategories([
                { id: 1, name: '로드 실패' },
            ]);
        });

        if (isEditing) {
            // 애니메이션
            axios.get(`/api/AdminAni/${id}`).then(res => setFormData(res.data));

            // 이미지
            axios.get(`/api/files`, {
                params: { targetType: 'ANILIST', targetId: id }
            })
                .then(res => {
                    if (res.data && res.data.length > 0) {
                        // 백엔드에서 준 파일 경로(URL)를 미리보기 state에 저장
                        setPreviewUrl(res.data[0].fileUrl);
                    }
                });

            //캐릭터
            axios.get(`/api/AdminChaBoard/ani/${id}`)
                .then(res => {
                    console.log("불러온 캐릭터 목록:", res.data);
                    setCharacters(res.data);
                })
                .catch(err => console.error("캐릭터 목록 로딩 실패:", err));
        }
    }, [id, isEditing]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevData => ({ ...prevData, [name]: value }));
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const url = URL.createObjectURL(file);
            setPreviewUrl(url);
            setMainImage(file);
        }
    };

    const handleImageRemove = () => {
        setMainImage(null);
        setPreviewUrl(null);
        if (fileInputRef.current) fileInputRef.current.value = "";
    };

    const handleDeleteCharacter = (id) => {
        if (id) {
            setDeletedCharIds(prev => [...prev, id]);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        try {
            let targetId = id;
            if (isEditing) {
                await axios.put(`/api/AdminAni/${id}`, formData);
            } else {
                const response = await axios.post('/api/AdminAni', formData);
                targetId = response.data.id; // DB에서 자동 생성된 ID (Primary Key)
            }

            //메인 이미지
            if (mainImage && mainImage instanceof File) {
                const imageFormData = new FormData();

                imageFormData.append('targetType', 'ANILIST');
                imageFormData.append('targetId', targetId);
                imageFormData.append('files', mainImage);

                await axios.post('/api/files/upload', imageFormData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });
            }

            // 캐릭터
            if (deletedCharIds.length > 0) {
                for (const deleteId of deletedCharIds) {
                    await axios.delete(`/api/AdminChaBoard/${deleteId}`);
                }
                console.log("삭제 완료된 ID들:", deletedCharIds);
            }

            for (const char of characters) {
                const charRes = await axios.post('/api/AdminChaBoard', {
                    id: char.id || null,
                    aniId: targetId,
                    name: char.name,
                    cvId: char.cvId || 1
                });

                const savedCharId = charRes.data.id;

                if (char.image instanceof File) {
                    const fileFormData = new FormData();
                    fileFormData.append('targetType', 'CHARACTER');
                    fileFormData.append('targetId', savedCharId);
                    fileFormData.append('files', char.image);

                    await axios.post('/api/files/upload', fileFormData, {
                        headers: { 'Content-Type': 'multipart/form-data' }
                    });
                }
            }

            alert(isEditing ? "수정되었습니다." : "등록되었습니다.");
            navigate('/AdminAni');

        } catch (error) {
            console.error("저장 실패:", error);
            // 에러 메시지 처리 강화
            const errorMessage = error.response?.data || error.message || "저장 중 오류가 발생했습니다.";
            alert(`저장 실패: ${errorMessage}`);
        }
    };

    const handleGoBack = () => {
        navigate('/AdminAni');
    }

    const inputClass = "w-full p-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent transition";
    const labelClass = "block text-sm font-bold text-slate-600 mb-2";

    return (
        <div className="max-w-4xl mx-auto pb-20">
            <div className="flex justify-between items-center mb-10">
                 <button onClick={handleGoBack} className="flex items-center gap-2 text-slate-500 font-bold hover:text-primary transition-colors">
                    <ArrowLeft size={20} />
                    <span>목록으로</span>
                </button>
                <h2 className="text-3xl font-black text-slate-800 tracking-tight">
                    {isEditing ? `"${formData.title || ''}" 정보 수정` : '신규 애니메이션 등록'}
                </h2>
                <div className="w-24"></div>
            </div>

            <form onSubmit={handleSubmit}>
                <div className="bg-white p-8 rounded-2xl shadow-sm border border-slate-100 space-y-8">
                    {/* 메인 이미지 업로드 섹션 */}
                    <div className="flex flex-col items-center justify-center p-6 border-2 border-dashed border-slate-200 rounded-xl bg-slate-50">
                        <div className="relative w-48 h-64 bg-white rounded-lg shadow-sm overflow-hidden flex items-center justify-center">
                            {previewUrl ? (
                                <img src={previewUrl} alt="Preview" className="w-full h-full object-cover" />
                            ) : mainImage && mainImage.fileUrl ? (
                                <img src={mainImage.fileUrl} alt="Main" className="w-full h-full object-cover" />
                            ) : (
                                <div className="text-slate-300 flex flex-col items-center">
                                    <ImageIcon size={48} />
                                    <span className="text-xs mt-2">이미지 없음</span>
                                </div>
                            )}
                            
                            {(previewUrl || (mainImage && mainImage.fileUrl)) && (
                                <button 
                                    type="button"
                                    onClick={handleImageRemove}
                                    className="absolute top-2 right-2 p-1 bg-black/50 text-white rounded-full hover:bg-red-500 transition-colors"
                                >
                                    <X size={16} />
                                </button>
                            )}
                        </div>
                        <input 
                            type="file" 
                            ref={fileInputRef} 
                            onChange={handleImageChange} 
                            className="hidden" 
                            accept="image/*"
                        />
                        <button 
                            type="button" 
                            onClick={() => fileInputRef.current.click()}
                            className="mt-4 flex items-center gap-2 px-4 py-2 bg-white border border-slate-300 rounded-lg text-slate-600 font-bold hover:bg-slate-100 transition-colors"
                        >
                            <Upload size={18} />
                            이미지 {mainImage ? '변경' : '업로드'}
                        </button>
                        <p className="text-xs text-slate-400 mt-2">권장 사이즈: 300x400 (세로형 포스터)</p>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div className="md:col-span-2">
                            <label className={labelClass}>제목</label>
                            <input type="text" name="title" value={formData.title} onChange={handleChange} className={inputClass} required />
                        </div>
                        
                        <div>
                            <label className={labelClass}>감독</label>
                            <input type="text" name="director" value={formData.director} onChange={handleChange} className={inputClass} />
                        </div>
                        <div>
                            <label className={labelClass}>제작사</label>
                            <input type="text" name="studio" value={formData.studio} onChange={handleChange} className={inputClass} />
                        </div>
                        
                        <div>
                            <label className={labelClass}>방영 시작일</label>
                            <input type="date" name="date" value={formData.date} onChange={handleChange} className={inputClass} />
                        </div>
                        <div>
                            <label className={labelClass}>시청 등급</label>
                            <select name="grade" value={formData.grade} onChange={handleChange} className={inputClass}>
                                <option value="ALL">전체 관람가</option>
                                <option value="G12">12세 관람가</option>
                                <option value="G15">15세 관람가</option>
                                <option value="G19">19세 관람가</option>
                            </select>
                        </div>

                        <div>
                            <label className={labelClass}>카테고리</label>
                            <select name="cateId" value={formData.cateId} onChange={handleChange} className={inputClass} required>
                                <option value="">선택하세요</option>
                                {categories.map(cat => (
                                    <option key={cat.id} value={cat.id}>{cat.name}</option>
                                ))}
                            </select>
                        </div>
                        
                        <div className="md:col-span-2">
                            <label className={labelClass}>예고편 URL (YouTube 등)</label>
                            <input type="text" name="aniPv" value={formData.aniPv} onChange={handleChange} className={inputClass} placeholder="https://..." />
                        </div>

                        <div className="md:col-span-2">
                            <label className={labelClass}>줄거리 및 상세 설명</label>
                            <textarea 
                                name="description" 
                                value={formData.description} 
                                onChange={handleChange} 
                                className={`${inputClass} h-40 resize-none`} 
                                required 
                            />
                        </div>
                    </div>
                </div>
                
                {/* 캐릭터 관리 컴포넌트 */}
                <AdminAniCha characters={characters} setCharacters={setCharacters} onDelete={handleDeleteCharacter} />

                <div className="flex justify-end pt-6">
                    <button type="submit" className="flex items-center gap-2 bg-primary text-white px-6 py-3 rounded-lg font-bold shadow hover:shadow-lg hover:-translate-y-0.5 transition-all">
                        <Save size={18} />
                        {isEditing ? '수정 완료' : '등록하기'}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default AdminAniEdit;
