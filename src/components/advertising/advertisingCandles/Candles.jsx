import candles from './Candles.png'
import s from './Candles.module.css';

const Candles = () => {
    return (
        <div className={s.candles}>
            <img src={candles} alt='candles' />
        </div>
    )
}

export default Candles;