import s from './Present.module.css';
import present from './Present.png';

const Present = () => {
    return (
        <div className={s.present}>
            <img src={present} alt='present' />
        </div>    
    )
}

export default Present;