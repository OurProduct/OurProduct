import wheel from './Wheel.png'
import s from './Wheel.module.css';

const Wheel = () => {
    return (
        <div className={s.wheel}>
            <img src={wheel} alt='wheel' />
        </div>
    )
}

export default Wheel;