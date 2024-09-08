/*console.log("script loaded");

let currentTheme=getTheme(); 

changeTheme();

function changeTheme()
{
	document.querySelector('html').classList.add(currentTheme);
	
   const themeButton= document.querySelector("#theme");

   
   themeButton.addEventListener('click',(event)=>{     
	  
	  const oldTheme=currentTheme
	   
	   if(currentTheme=="dark")
	   {
		   currentTheme="light"
	   }else{
		   currentTheme="dark"
	   }
	  
	  
	  setTheme(currentTheme);
	   document.querySelector("html").classList.remove(oldTheme);
	  document.querySelector("html").classList.add(currentTheme);
	  themeButton.querySelector("span").textContent=currentTheme=='light' ? "Dark" : "Light";
	  
	  
	  
	  
   });
   
}

// local storage name ka ek object js me already hota hai usko use krke theme set krdi
function setTheme(theme)
{
	localStorage.setItem("theme",theme);
}

function getTheme()
{
	let theme=localStorage.getItem("theme");
	if(theme) return theme
	else return "dark"
}

*/


console.log("Script loaded");

// change theme work
let currentTheme = getTheme();
//initial -->

document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

//TODO:
function changeTheme() {
  //set to web page

  changePageTheme(currentTheme, "");
  //set the listener to change theme button
  const changeThemeButton = document.querySelector("#theme");

  changeThemeButton.addEventListener("click", (event) => {
    let oldTheme = currentTheme;
    console.log("change theme button clicked");
    if (currentTheme === "dark") {
      //theme ko light
      currentTheme = "light";
    } else {
      //theme ko dark
      currentTheme = "dark";
    }
    console.log(currentTheme);
    changePageTheme(currentTheme, oldTheme);
  });
}

//set theme to localstorage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

//get theme from localstorage
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "dark";
}

//change current page theme
function changePageTheme(theme, oldTheme) {
  //localstorage mein update karenge
  setTheme(currentTheme);
  //remove the current theme

  if (oldTheme) {
    document.querySelector("html").classList.remove(oldTheme);
  }
  //set the current theme
  document.querySelector("html").classList.add(theme);

  // change the text of button
  document
    .querySelector("#theme")
    .querySelector("span").textContent = theme == "light" ? "Dark" : "Light";
}

