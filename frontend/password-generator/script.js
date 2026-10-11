let inputSlider= document.getElementById("inputSlider");
let sliderValue= document.getElementById("sliderValue");
let passbox= document.getElementById("passbox");
let btn= document.getElementById("btn");
let symbols= document.getElementById("symbols");
let lowercase= document.getElementById("lowercase");
let uppercase= document.getElementById("uppercase");
let numbers= document.getElementById("numbers");
let copyIcon= document.getElementById("copyIcon");


sliderValue.textContent= inputSlider.value;
inputSlider.addEventListener("input", ()=>{
sliderValue.textContent= inputSlider.value;
});

let upperChars= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
let lowerChars= "abcdefghijklmnopqrstuvwxyz";
let numberChars= "0123456789";
let symbolChars= "!@#$%^&*()_+~`|}{[]:;?><,./-=";

btn.addEventListener("click", ()=>{
     passbox.value= generatePassword();
});

function generatePassword(){
    let genPassword= "";
    let allChars="";

    allChars+=lowercase.checked? lowerChars: "";
    allChars+=uppercase.checked? upperChars: "";
    allChars+=numbers.checked? numberChars: "";
    allChars+=symbols.checked? symbolChars: "";

    if(allChars==""||allChars.length==0){
        return genPassword;
    }

    let i=1; 
    while(i<=inputSlider.value){
      genPassword+=allChars.charAt( Math.floor(Math.random()* allChars.length));
      i++;
    }

    return genPassword;
}

copyIcon.addEventListener("click", ()=>{
    if(passbox.value.length!=0){
    navigator.clipboard.writeText(passbox.value);
    // copyIcon.innerText= "check";
    copyIcon.title= "Copied";

    // setTimeout(()=>{
    //     copyIcon.innerText= "content_copy";
    //     copyIcon.title= "Copy to clipboard";
    // }, 2000);
}
});