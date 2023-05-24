import s from './Main.module.css'
import AboutUs from "../aboutUs/AboutUs"
import CarouselImages from "../carousel/CarouselImages"
import ForArtisans from "../forArtisans/ForArtisans"
import Menu from "../menu/Menu"
import OurProduct from "../ourProduct/OurProduct"

const Main = () => {
    return (
        <div className={s.app}>
            <OurProduct />
            <Menu />
            <AboutUs />
            <CarouselImages />
            <ForArtisans />
        </div>
    )
}

export default Main;