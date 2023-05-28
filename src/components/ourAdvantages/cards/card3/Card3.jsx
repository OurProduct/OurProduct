import card3 from './Card3.png'
import s from './Card3.module.css';

const Card3 = () => {
    return (
        <div className={s.card3}>

            <div className={s.card_header}>
                Быстрое и удобное добавление товаров
            </div>

            <div className={s.card_description}>
                Возможность легко и быстро загрузить каталог товаров
            </div>

            <div className={s.card_img}>
                <img src={card3} alt='card3' />
            </div>
        </div>
    )
}

export default Card3;