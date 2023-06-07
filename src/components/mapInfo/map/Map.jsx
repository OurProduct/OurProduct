import s from './Map.module.css'
import map from './Map1.png'

const Map = () => {
    return (
        <div className={s.map}>
            <img src={map} alt='map' />
        </div>
    )
}

export default Map;