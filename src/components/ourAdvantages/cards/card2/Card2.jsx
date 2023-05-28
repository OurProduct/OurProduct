import card2 from './Card2.png'
import s from './Card2.module.css';

const Card2 = () => {
    return (
        <div className={s.card2}>

            <div className={s.card_header}>
                Бесплатное подключение и обслуживание
            </div>

            <div className={s.card_description}>
                Воспользуйтесь всеми возможностями экосистемы абсолютно бесплатно
            </div>

            <div className={s.card_img}>
                <img src={card2} alt='card2' />
            </div>
        </div>
    )
}

export default Card2;