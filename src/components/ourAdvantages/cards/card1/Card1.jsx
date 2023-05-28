import card1 from './Card1.png'
import s from './Card1.module.css';

const Card1 = () => {
    return (
        <div className={s.card1}>

            <div className={s.card_header}>Привлечение покупателей берем на себя</div>

            <div className={s.card_description}>
                Вам остается разместить товары
            </div>

            <div className={s.card_img}>
                <img src={card1} alt='card1' />
            </div>
        </div>
    )
}

export default Card1;