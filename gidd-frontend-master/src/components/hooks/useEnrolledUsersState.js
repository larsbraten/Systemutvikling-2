import { useState } from "react";

export default initialList => {
    const [userList, setUserList] = useState(initialList);
    return {
        userList,
        addUser: newUser => {
            setUserList([...userList, newUser]); // todo update??
        },
        removeUser: userId => {
            //filter out removed item
            const updatedList = userList.filter(user => user.id !== userId);
            setUserList(updatedList);
        },
        // toggleItem: userId => {
        //     const updatedList = userList.map(user =>
        //         user.id === userId ? { ...user, ensured: !item.ensured } : item
        //     );
        //     setUserList(updatedList);
        // },
        // editItem: (itemId, newItem) => {
        //     // const updatedList = list.map(item =>
        //     //     item.id === itemId ? { ...item, task: newTask } : todo
        //     // );
        //     // setTodos(updatedTodos);
        // }
    };
};