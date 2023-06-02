import { NavLink } from 'react-router-dom';
import s from './LowerText.module.css'

const LowerText = () => {
    return (
        <div className={s.lower_text}>
            <div className={s.header_text}>
                <h4>
                    Создайте свою витрину и получайте заказы, а мы поможем с продвижением
                </h4>
            </div>
            <div className={s.button}>
                <NavLink to='/start-selling'>Начать продавать</NavLink>
            </div>
        </div>
    )
}

export default LowerText;