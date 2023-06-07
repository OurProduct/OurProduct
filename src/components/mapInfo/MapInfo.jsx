import s from './MapInfo.module.css'
import InfoHeader from './infoHeader/InfoHeader.jsx';
import InfoNumbers from './infoNumbers/InfoNumbers.jsx';
import Map from './map/Map.jsx';

const MapInfo = () => {
    return (
        <div className={s.map_info}>
            <InfoHeader />
            <InfoNumbers />
            <Map />
        </div>
    )
}

export default MapInfo;