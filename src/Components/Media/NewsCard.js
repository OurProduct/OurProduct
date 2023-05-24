const NewsCard = (props) => {
    return (
        <div className={`news-card ${props.item.id === 1 ? 'news-card--first' : ''}`}>
            <img src={`./img/${props.item.img}`} alt="" />
            <div className={`news-card__button button ${props.item.id === 1 ? 'news-card__button--first' : ''}`}>{props.item.btndesc}</div>
        </div>
    )
}
export default NewsCard
