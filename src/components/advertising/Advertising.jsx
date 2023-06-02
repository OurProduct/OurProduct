import s from './Advertising.module.css';
import Cake from './advertisingCake/Cake.jsx';
import Candles from './advertisingCandles/Candles.jsx';
import LowerText from './advertisingLowerText/LowerText';
import Present from './advertisingPresent/Present.jsx';
import TopText from './advertisingTopText/TopText.jsx';
import Wheel from './advertisingWheel/Wheel.jsx';

const Advertising = () => {
    return (
        <div className={s.advertising}>
            <Candles />
            <Wheel />
            <Cake />
            <Present />
            <TopText />
            <LowerText />
        </div>
    )
}

export default Advertising;