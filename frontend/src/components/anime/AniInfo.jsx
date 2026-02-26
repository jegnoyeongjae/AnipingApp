import { useEffect,useState } from "react";
import { useParams } from "react-router-dom";
import "./AniInfo.css";

const AniInfo=({data})=>{
    const [anime,setAnime]=useState(null)
    const [likes,setLikes]=useState(0);
    const [saved,setSaved]=useState(false);

    const handleLike=()=>{
        setLikes(likes+1)
    };

    const handleSave=()=>{
        setSaved(!saved);
    };

    return(
        <div className="aniInfo">
            <img src={data.infoImg} alt={data.title} className="aniInfoImage"/>
            <div className="aniInfoDetails">
                <h2>{data.title}</h2>
                <p><strong>감독:</strong>{data.director}</p>
                <p><strong>제작사:</strong>{data.studio}</p>
                <p><strong>장르:</strong>{data.genre}</p>
                <p><strong>방영일:</strong>{data.date}</p>
                <p><strong>등급:</strong>{data.grade}</p>
                
                <div className="aniInfoAction"> {/*좋아요 다시 누르면 취소되게 */}
                    <button className="likeBtn" onClick={handleLike}>
                        ❤️ {likes}
                    </button>
                    <button className="saveBtn" onClick={handleSave}>
                        {saved? "☆즐겨찾기 등록":"⭐즐겨찾기 해제"}
                    </button>
                </div>
            </div>
        </div>
    )
}

export default AniInfo;
