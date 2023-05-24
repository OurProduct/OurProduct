import  { useState } from 'react'
import '../../styles/categories.css'


const Categories = [
    {
        key: 'all',
        name: 'Все',
    },
    {
        key: 'Articles',
        name: 'Статьи',        
    },
    {
        key: 'News',
        name: 'Новости',       
    },
    {
        key: 'Stories',
        name: 'Истории успеха',
    },
    {
        key: 'Сourses',
        name: 'Курсы',
    }
]



const Category = (props) => {
    const [selectedCategory, setSelectedCategory] = useState('all');

    const handleCategoryClick = (categoryKey) => {
        setSelectedCategory(categoryKey);
        props.chooseCategory(categoryKey);
    };

return (
    <div className="categories">
        {Categories.map((category) => (
            <div
                className={selectedCategory === category.key ? 'active' : 'unActive'}
                key={category.key}
                onClick={() => handleCategoryClick(category.key)}>
                    {category.name}
            </div>
    ))}
    </div>
);
};

export default Category
