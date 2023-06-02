import s from './TopText.module.css'

const TopText = () => {
    return (
        <div className={s.top_text}>
            <div className={s.header_text}>
                <h4>
                    Онлайн платформа для бизнеса и покупателей
                </h4>
            </div>
            <div className={s.body_text}>
                Мы предоставляем доступ к ресурсам города
                с целью раскрыть потенциал локального бизнеса
                и привлечь интерес граждан к продукции местных предпринимателей.
            </div>
        </div>
    )
}

export default TopText;