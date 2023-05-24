const ArtcCard = (props) => {
  return (
    <div className="artcile-card">
        <h2>{props.item.title}</h2>
        <img src={`./img/${props.item.img}`} alt="" />
        <div className={`artc-card__button button ${props.item.id == 1 ? 'artc-card__button--first' : ''}`}>Посмотреть</div>
    </div>
  )
} 
export default ArtcCard
