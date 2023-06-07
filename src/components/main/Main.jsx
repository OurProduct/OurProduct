import s from './Main.module.css';
import AboutUs from "../aboutUs/AboutUs.jsx";
import CarouselImages from "../carousel/CarouselImages.jsx";
import StartSelling from "../startSelling/StartSelling.jsx";
import Menu from "../menu/Menu.jsx";
import OurProduct from "../ourProduct/OurProduct.jsx";
import OurAdvantages from '../ourAdvantages/OurAdvantages.jsx';
import Advertising from '../advertising/Advertising';
import MapInfo from '../mapInfo/MapInfo';

const Main = () => {
    return (
        <div className={s.app}>
            <OurProduct />
            <Menu />
            <AboutUs />
            <CarouselImages />
            <StartSelling />
            <OurAdvantages />
            <Advertising />
            <MapInfo />
        </div>
    )
}

export default Main;