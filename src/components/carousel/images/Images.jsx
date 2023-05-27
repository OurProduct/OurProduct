import s from './Images.module.css';
import Wheel from "./wheel/Wheel";
import Candles from './candles/Candles';
import PresentThreads from './present&threads/PresentThreads';
import Photographer from './photographer/Photographer';
import Cake from './cake/Cake';

const Images = () => {
    return (
        <div className={s.images}>
            <Wheel />
            <PresentThreads />
            <Candles />
            <Photographer />
            <Cake />
        </div>    
    )
}

export default Images;