import card4 from './Card4.png'
import s from './Card4.module.css';

const Card4 = () => {
    return (
        <div className={s.card4}>

            <div className={s.card_header}>
                Поможем с продвижением
            </div>

            <div className={s.card_description}>
                Мы оказываем помощь с момента создания каталога до Вашей первой сделки
            </div>

            <div className={s.card_img}>
                <img src={card4} alt='card4' />
            </div>
        </div>
    )
}

export default Card4;