import NewsCard from './NewsCard'


const showNewsCards = (props) => {
    return(
        <div className='news-cards__wrapper'>
            {props.cards.map(item => {
                return <NewsCard  key={item.id} item={item} /> 
            })}
        </div>
    )
}

const showNothing = () => {
    return(
        <div className='Empty'>
            <h2>Новостей нет</h2>
        </div>
    )
}

const News = (props) => {
    return (
        <div>
            {props.cards.length > 0 ? showNewsCards(props) : showNothing()}
        </div>
    )
}
export default News
