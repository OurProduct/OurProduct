import present from './Present.png'
import s from './Present.module.css';

const Present = () => {
    return (
        <div className={s.present}>
            <img src={present} alt='present' />
        </div>
    )
}

export default Present;