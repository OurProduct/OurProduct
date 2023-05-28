import s from './CarouselImages.module.css'
import Images from './images/Images.jsx';

const CarouselImages = () => {
    return (
        <div className={s.carousel}>
            <Images />
        </div>
    )
}

export default CarouselImages;