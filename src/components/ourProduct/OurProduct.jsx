import icon from './free-icon-location-8866624.png';
import mainlLogo from './MainLogo.png';
import s from './OurProduct.module.css';

const OurProduct = () => {
    return (
        <div className={s.product}>
            <div className={s.productIcon}>
                <img src={icon} alt='icon' />
                <button>Your city</button>
            </div>
            <div className={s.divLogo}>
                <img src={mainlLogo} alt='logo' />
            </div>
        </div>
    )
}

export default OurProduct;