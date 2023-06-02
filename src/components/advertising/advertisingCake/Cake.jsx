import cake from './Cake.png'
import s from './Cake.module.css';

const Cake = () => {
    return (
        <div className={s.cake}>
            <img src={cake} alt='cake' />
        </div>
    )
}

export default Cake;