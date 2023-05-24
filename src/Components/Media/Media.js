import Category from "./Categories"
import { useState } from "react";
import ArtcCards from "./ArtcCards";
import News from "./News";
import '../../styles/artcCards.css'
import '../../styles/news.css'
import '../../styles/media.css'

const cards = [
    {
        id: '1',
        title: 'Как работать быстро и эффективно: учит Макс Черепица',
        img: 'artc-bg.png'
    },
    {
        id: '2',
        title: 'afdafa2',
        img: 'artc-bg.png'
    },
    {
        id: '3',
        title: 'afdafa3',
        img: 'artc-bg.png'
    },
    {
        id: '4',
        title: 'afdafa4',
        img: 'artc-bg.png'
    },
    {
        id: '5',
        title: 'afdafa5',
        img: 'artc-bg.png'
    },
]

const NewsCards = [
    {
        id: 1,
        img: '1News.png',
        btndesc: 'Финансы',
    },
    {
        id: 2,
        img: '2News.png',
        btndesc: 'Маркетинг',
    },
    {
        id: 3,
        img: '3News.png',
        btndesc: 'Тренинги',
    },
    {
        id: 4,
        img: '4News.png',
        btndesc: 'мероприятия сообщества',
    },
    {
        id: 5,
        img: '5News.png',
        btndesc: 'краудфандинг',
    },
]

const Media = () => {
    const [items, setItems] = useState(cards);
    let [currentItems, setCurrentItems] = useState();

    const chooseCategory = (category) => {

        if (category === 'all') {
            setCurrentItems([...items])
            return
        }
        currentItems = items.filter(item => item.category === category)
        setCurrentItems(currentItems)

    }

    return (
        <div>
            <div className="container"> <Category chooseCategory={chooseCategory} /></div>
            <section>
                <div className="container">
                    <h2>Актуальные статьи</h2>
                    <div className="articles">
                        <ArtcCards cards={cards}/>
                    </div>
                    <div className="media-button">Показать еще</div>
                    </div>
            </section>


            <section>
                <div className="container">
                    <h2>Сейчас популярно</h2>
                    <div className="articles">
                        <ArtcCards cards={cards}/>
                    </div>
                    <div className="media-button">Показать еще</div>
                </div>
            </section>


            <section className="news">
                <div className="container">
                    <div className="news-wrapper">
                        <h2>Новости</h2>
                        <News cards={NewsCards}/>
                    </div>
                </div>
            </section>


            <section>
                <div className="container">
                    <h2>Истории успеха</h2>
                </div>
            </section>


            <section>
            <div className="container"> 
                <h2>Образовательные курсы для бизнесменов</h2>
            </div>
            </section>
        </div>
    )
}
export default Media