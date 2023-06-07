import s from './InfoNumbers.module.css'

const InfoNumbers = () => {
    return (
        <div className={s.info_numbers}>

            <div className={s.cards}>
                <div className={s.numbers}>
                    33
                </div>
                <div className={s.info}>
                    Предприятий зарегистрировано
                </div>
            </div>

            <div className={s.cards}>
                <div className={s.numbers}>
                    1859
                </div>
                <div className={s.info}>
                    Количество размещенных услуг
                </div>
            </div>

            <div className={s.cards}>
                <div className={s.numbers}>
                    235
                </div>
                <div className={s.info}>
                    Количество размещенных товаров
                </div>
            </div>

        </div>
    )
}

export default InfoNumbers;