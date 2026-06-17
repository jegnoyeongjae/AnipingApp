import { CheckCircle, XCircle, Trash2, Clock } from 'lucide-react';

const AdminChaFLLi = ({chaFl, onApprove, onReject, onDelete}) => {

    const formatDate = (dateString) => {
        if (!dateString) return "-";
        const date = new Date(dateString);
        return date.toLocaleDateString('ko-KR', {
            year: '2-digit',
            month: '2-digit',
            day: '2-digit'
        });
    };

    return(
        <li className="grid grid-cols-12 gap-4 p-5 items-center border-b border-slate-100 last:border-b-0">
            <div className="col-span-1 flex justify-center">
                <img src={chaFl.charImage} alt={chaFl.charName} className="w-14 h-14 object-cover rounded-lg shadow-sm border border-slate-200"/>
            </div>
            <div className="col-span-3 font-bold text-slate-800 text-base truncate pr-4">{chaFl.charName}</div>
            <div className="col-span-4 text-slate-600 text-base italic truncate pr-4">"{chaFl.content}"</div>
            <div className="col-span-1 text-center text-slate-500 text-sm">{chaFl.userNickname}</div>
            <div className="col-span-1 text-center text-slate-400 text-sm">{formatDate(chaFl.createAt)}</div>
            <div className="col-span-2 flex items-center justify-center gap-2">
                {chaFl.active === 'accept' ? (
                    <>
                        <span className="flex items-center gap-1 text-xs font-bold text-green-600 bg-green-100 px-2.5 py-1 rounded-full">
                            <CheckCircle size={12} /> 등록완료
                        </span>
                        <button
                            onClick={onDelete}
                            className="p-2 text-slate-300 hover:bg-red-50 hover:text-red-500 rounded-full transition-all"
                            title="삭제"
                        >
                            <Trash2 size={16} />
                        </button>
                    </>
                ) : chaFl.active === 'reject' ? (
                    <>
                        <span className="flex items-center gap-1 text-xs font-bold text-red-600 bg-red-100 px-2.5 py-1 rounded-full">
                            <XCircle size={12} /> 거절됨
                        </span>
                        <button
                            onClick={onDelete}
                            className="p-2 text-slate-300 hover:bg-red-50 hover:text-red-500 rounded-full transition-all"
                            title="삭제"
                        >
                            <Trash2 size={16} />
                        </button>
                    </>
                ) : (
                    <>
                        <span className="flex items-center gap-1 text-xs font-bold text-orange-600 bg-orange-100 px-2.5 py-1 rounded-full">
                            <Clock size={12} /> 신청대기
                        </span>
                        <div className="flex gap-1 ml-1">
                            <button
                                onClick={onApprove}
                                className="p-2 text-slate-400 hover:bg-green-100 hover:text-green-600 rounded-full transition-all"
                                title="승인"
                            >
                                <CheckCircle size={16} />
                            </button>
                            <button
                                onClick={onReject}
                                className="p-2 text-slate-400 hover:bg-red-50 hover:text-red-500 rounded-full transition-all"
                                title="거절"
                            >
                                <XCircle size={16} />
                            </button>
                        </div>
                    </>
                )}
            </div>
        </li>
    )
}

export default AdminChaFLLi;
