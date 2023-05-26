import { NavLink } from 'react-router-dom';
import s from './StartSelling.module.css'

const StartSelling = () => {
    return (
        <div className={s.main}>
            <div className={s.selling}>
                <div>
                    Начните продавать свою продукцию
                </div>
                <div>
                    напрямую потребителям
                </div>
            </div>

            <div className={s.hobby}>
                Преврати свое хобби в доход
            </div>

            <div className={s.button}>
                <NavLink to='/start-selling'>Начать продавать</NavLink>
            </div>
        </div>
    )
}

export default StartSelling;