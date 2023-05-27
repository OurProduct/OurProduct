import Present from "./present/Present";
import Threads from "./threads/Threads";
import s from './PresentThreads.module.css'

const PresentThreads = () => {
    return (
        <div className={s.present_threads}>
            <Present />
            <Threads />
        </div>    
    )
}

export default PresentThreads;