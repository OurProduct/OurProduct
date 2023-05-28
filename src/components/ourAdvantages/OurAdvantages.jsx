import s from './OurAdvantages.module.css';
import AdvantagesText from './advantagesText/AdvantagesText.jsx';
import Card1 from './cards/card1/Card1';
import Card2 from './cards/card2/Card2';
import Card3 from './cards/card3/Card3';
import Card4 from './cards/card4/Card4';

const OurAdvantages = () => {
    return (
        <div className={s.our_advantages}>
            <AdvantagesText />
            <Card1 />
            <Card2 />
            <Card3 />
            <Card4 />
        </div>
    )
}

export default OurAdvantages;