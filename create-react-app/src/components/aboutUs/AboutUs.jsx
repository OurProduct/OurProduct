import logo from './images.png'
import { NavLink } from 'react-router-dom';
import s from './AboutUs.module.css'

const AboutUs = () => {
    return (
        <div className={s.about}>
            <NavLink to='/event-calendar'>Календарь мероприятий</NavLink>
            <NavLink to='/faq'>FAQ</NavLink>
            <NavLink to='/about-us'>О нас</NavLink>
            <NavLink to='/entrance'>
                <img src={logo} alt='logo' />
                Вход
            </NavLink>
        </div>
    )
}

export default AboutUs;