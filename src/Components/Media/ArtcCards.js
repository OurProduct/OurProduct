import ArtcCard from './ArtcCard'

const showArtcCards = (props) => {
    return(
        <div className='artc-cards__wrapper'>
            {props.cards.map(item => {
                return <ArtcCard  key={item.id} item={item} /> 
            })}
        </div>
    )
}

const showNothing = () => {
    return(
        <div className='Empty'>
            <h2>Статей нет</h2>
        </div>
    )
}

const ArtcCards = (props) => {
    return (
        <div>
            {props.cards.length > 0 ? showArtcCards(props) : showNothing()}
        </div>
    )
}
export default ArtcCards
