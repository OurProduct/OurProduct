import { NavLink } from 'react-router-dom';
import s from './Menu.module.css';

const Menu = () => {
    return (
        <div className={s.menu}>
            <NavLink to='/showcase'>Витрина</NavLink>
            <NavLink to='/media'>Медиа</NavLink>
            <NavLink to='/club'>Клуб</NavLink>
        </div>
    )
}

export default Menu;