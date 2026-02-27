import React, { useState, useEffect } from 'react';
import { Plus, Trash2, Upload, ImageIcon } from 'lucide-react';
import axios from 'axios';

const AdminAniCha = ({ characters, setCharacters, onDelete }) => {
    const [voiceActors, setVoiceActors] = useState([]); // 성우 목록

    useEffect(() => {
        axios.get('/api/AdminVA')
            .then(res => {
                setVoiceActors(res.data);
            })
            .catch(err => console.error("성우 목록 로딩 실패:", err));
    }, []);

    const addCharacter = () => {
        setCharacters([...characters, {
            id: null, // DB ID는 null로!
            tempId: Date.now(), // 리액트 key용 임시 ID
            name: '',
            cvId: '',
            image: null,
            previewUrl: null
        }]);
    };

    const handleFieldChange = (target, field, value) => {
        setCharacters(characters.map(char => {
            const isMatch = char.id ? char.id === target.id : char.tempId === target.tempId;
            return isMatch ? { ...char, [field]: value } : char;
        }));
    };

    const removeCharacter = (char) => {
        if (window.confirm("캐릭터를 삭제하시겠습니까?")) {
            if (char.id) {
                onDelete(char.id);
            }
            setCharacters(characters.filter(c =>
                char.id ? c.id !== char.id : c.tempId !== char.tempId
            ));
        }
    };

    const handleInputChange = (targetChar, field, value) => {
        setCharacters(prev => prev.map(char => {
            const isMatch = targetChar.id
                ? char.id === targetChar.id
                : char.tempId === targetChar.tempId;

            return isMatch ? { ...char, [field]: value } : char;
        }));
    };

    const handleImageChange = (id, e) => {
        const file = e.target.files[0];
        if (file) {
            const url = URL.createObjectURL(file);
            setCharacters(characters.map(char =>
                char.id === id ? { ...char, image: file, previewUrl: url } : char
            ));
        }
    };

    return (
        <div className="mt-8 bg-white p-8 rounded-2xl shadow-sm border border-slate-100">
            <div className="flex justify-between items-center mb-6">
                <h3 className="text-xl font-bold text-slate-800">등장인물 (캐릭터) 관리</h3>
            </div>

            <div className="space-y-4">
                {characters.map((char) => (
                    <div key={char.id} className="flex gap-4 items-start p-4 bg-slate-50 rounded-xl border border-slate-200">
                        {/* 이미지 업로드 */}
                        <div className="relative w-24 h-32 bg-white rounded-lg border border-slate-300 flex items-center justify-center overflow-hidden flex-shrink-0 group">
                            {char.previewUrl || char.image ? (
                                <img
                                    src={
                                        char.previewUrl || // 1. 방금 파일 선택한 경우 (Blob URL)
                                        char.image      || // 2. DB에서 가져온 경우 (S3 URL)
                                        "/default-profile.png"
                                    }
                                    className="w-full h-full object-cover"
                                    alt={char.name || "캐릭터 이미지"}
                                    onError={(e) => {
                                        console.log("이미지 로딩 실패 주소:", e.target.src);
                                        e.target.src = "/default-profile.png";
                                    }}
                                />
                            ) : (
                                <ImageIcon className="text-slate-300" />
                            )}
                            <label className="absolute inset-0 bg-black/50 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity cursor-pointer text-white">
                                <Upload size={20} />
                                <input type="file" className="hidden" accept="image/*" onChange={(e) => handleImageChange(char.id, e)} />
                            </label>
                        </div>

                        {/* 정보 입력 */}
                        <div className="flex-1 grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div>
                                <label className="block text-xs font-bold text-slate-500 mb-1">캐릭터 이름</label>
                                <input 
                                    type="text" 
                                    value={char.name} 
                                    onChange={(e) => handleInputChange(char, 'name', e.target.value)}
                                    className="w-full p-2 border border-slate-300 rounded-lg text-sm focus:outline-none focus:border-primary"
                                    placeholder="이름 입력"
                                />
                            </div>
                            <div>
                                <label className="block text-xs font-bold text-slate-500 mb-1">성우 (CV)</label>
                                <select 
                                    value={char.cvId} 
                                    onChange={(e) => handleInputChange(char, 'cvId', e.target.value)}
                                    className="w-full p-2 border border-slate-300 rounded-lg text-sm focus:outline-none focus:border-primary"
                                >
                                    <option value="">성우 선택</option>
                                    {voiceActors.map(va => (
                                        <option key={va.id} value={va.id}>{va.name}</option>
                                    ))}
                                </select>
                            </div>
                        </div>

                        {/* 삭제 버튼 */}
                        <button 
                            onClick={() => removeCharacter(char)}
                            className="p-2 text-slate-400 hover:text-red-500 hover:bg-red-50 rounded-lg transition-colors"
                        >
                            <Trash2 size={20} />
                        </button>
                    </div>
                ))}

                <button 
                    type="button"
                    onClick={addCharacter}
                    className="w-full py-3 border-2 border-dashed border-slate-300 rounded-xl text-slate-500 font-bold hover:border-primary hover:text-primary hover:bg-blue-50 transition-all flex items-center justify-center gap-2"
                >
                    <Plus size={20} /> 캐릭터 추가
                </button>
            </div>
        </div>
    );
};

export default AdminAniCha;
