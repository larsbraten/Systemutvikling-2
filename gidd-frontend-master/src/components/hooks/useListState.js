import { useState } from "react";

export default initialList => {
    const [list, setList] = useState(initialList);
    return {
        list,
        addItem: newItem => {
            setList([...list, newItem]); // todo update??
        },
        removeItem: itemId => {
            //filter out removed item
            const updatedList = list.filter(item => item.id !== itemId);
            setList(updatedList);
        },
        toggleItem: itemId => {
            const updatedList = list.map(item =>
                item.id === itemId ? { ...item, ensured: !item.ensured } : item
            );
            setList(updatedList);
        },
        editItem: (itemId, newItem) => {
            // const updatedList = list.map(item =>
            //     item.id === itemId ? { ...item, task: newTask } : todo
            // );
            // setTodos(updatedTodos);
        }
    };
};