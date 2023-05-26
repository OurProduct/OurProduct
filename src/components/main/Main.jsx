import s from './Main.module.css';
import AboutUs from "../aboutUs/AboutUs.jsx";
import CarouselImages from "../carousel/CarouselImages.jsx";
import StartSelling from "../startSelling/StartSelling.jsx";
import Menu from "../menu/Menu.jsx";
import OurProduct from "../ourProduct/OurProduct.jsx";

const Main = () => {
    return (
        <div className={s.app}>
            <OurProduct />
            <Menu />
            <AboutUs />
            <CarouselImages />
            <StartSelling />
        </div>
    )
}

export default Main;