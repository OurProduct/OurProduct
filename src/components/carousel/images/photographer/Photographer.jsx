import photographer from './Photographer.png';
import s from './Photographer.module.css';

const Photographer = () => {
    return (
        <div className={s.photographer}>
            <img src={photographer} alt="photographer" />
        </div>    
    )
}

export default Photographer;