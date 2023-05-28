import s from './Main.module.css';
import AboutUs from "../aboutUs/AboutUs.jsx";
import CarouselImages from "../carousel/CarouselImages.jsx";
import StartSelling from "../startSelling/StartSelling.jsx";
import Menu from "../menu/Menu.jsx";
import OurProduct from "../ourProduct/OurProduct.jsx";
import OurAdvantages from '../ourAdvantages/OurAdvantages';

const Main = () => {
    return (
        <div className={s.app}>
            <OurProduct />
            <Menu />
            <AboutUs />
            <CarouselImages />
            <StartSelling />
            <OurAdvantages />
        </div>
    )
}

export default Main;