import Cake from "./cake/Cake"
import ThreadsTwo from "./threadsTwo/ThreadsTwo";
import s from './CakeThreads.module.css'

const CakeThreads = () => {
    return (
        <div className={s.cakeThreads}>
            <Cake />
            <ThreadsTwo />
        </div>    
    )
}

export default CakeThreads;