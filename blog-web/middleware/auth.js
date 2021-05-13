export default ( {store , route , redirect , req}) => {
    if(!store || !store.state.userInfo){
        const redirectURL = 'http://' + req.headers.host + route.path
        console.log(`http:${process.env.authURL}?redirectURL=${redirectURL}`)
        redirect(`http:${process.env.authURL}?redirectURL=${redirectURL}`)
    }
}