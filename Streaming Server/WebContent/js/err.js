(function(){
  
  var DISPLAY_WIDTH = window.innerWidth,
    DISPLAY_HEIGHT = window.innerHeight,
	DISPLAY_DURATION = 10;
	
	var	mouse = { x: 0, y: 0 },
		container,
		canvas,
		context,
		startTime,
		wip={w: 0.5},
		nrf=3.0,
		eyes;
	
	function norm(){
		mouse.x=0.0;
		mouse.y=DISPLAY_HEIGHT;
	}
	function norm2(){
		mouse.x=DISPLAY_WIDTH;
		mouse.y=DISPLAY_HEIGHT;
	}
	function norm3(){
		mouse.x=DISPLAY_WIDTH/2;
		mouse.y=100;
		nrf=0.5;
	}
	
	function pupilaa(){
		if(wip.w>nrf){
			wip.w=wip.w-0.1;
		}
		if(wip.w<nrf){
			wip.w=wip.w+0.3;
		}
	}
	
	function initialize() {
		
		container = document.querySelector( '.fof' );
		canvas = document.querySelector( '.fof>canvas' );
		window.addEventListener('contextmenu', event => event.preventDefault());
		if( canvas ) {
			canvas.width = DISPLAY_WIDTH;
			canvas.height = DISPLAY_HEIGHT;
			
			context = canvas.getContext( '2d' );
			var ua = navigator.userAgent.toLowerCase();
			var isAndroid = ua.indexOf("android") > -1;//ua.indexOf("mobile");
				//ua.indexOf("android") > -1; 
				
			if(!isAndroid) {
				//PC
			window.addEventListener( 'resize', function( event ) {
//				DISPLAY_DURATION = 10;
//				DISPLAY_WIDTH=event.target.outerWidth;
//				DISPLAY_HEIGHT=event.target.outerHeight;
//				mouse={ x: 0, y: 0 };
//				canvas = null;
//				context=null;
//				startTime=null;
//				eyes=null;
//				initialize()
				location.reload();
			}, false );
			var myVar = setTimeout(norm, 1000);
			var myVar1 = setTimeout(norm2, 2000);
			var myVar2 = setTimeout(norm3, 3000);
			window.addEventListener( 'mousemove', function( event ) {
				clearTimeout(myVar)
				clearTimeout(myVar1);
			    clearTimeout(myVar2);
				mouse.x = event.clientX;
				mouse.y = event.clientY;
				nrf=3.0;
			}, false );
			
			window.addEventListener( 'mouseout', function( event ) {
				clearTimeout(myVar)
				clearTimeout(myVar1);
			    clearTimeout(myVar2);
				myVar = setTimeout(norm, 1000);
				myVar1 = setTimeout(norm2, 2000);
				myVar2 = setTimeout(norm3, 3000);
			}, false );
			}else{
				//ANDROID
				var myVar = setTimeout(norm, 1000);
				var myVar1 = setTimeout(norm2, 2000);
				var myVar2 = setTimeout(norm3, 3000);
				window.addEventListener('orientationchange', function() {
					location.reload();
				}, false);
				window.addEventListener( 'mousemove', function( event ) {
					nrf=3.0;
					clearTimeout(myVar)
					clearTimeout(myVar1);
				    clearTimeout(myVar2);
					myVar = setTimeout(norm, 3000);
					myVar1 = setTimeout(norm2, 4000);
					myVar2 = setTimeout(norm3, 5000);
					mouse.x = event.clientX;
					mouse.y = event.clientY;
				}, false );
			}
			
			eyes = [
	
        new Eye( canvas,   0.50, 0.12,   2.70,    0.10 ),
			];
			
			startTime = Date.now();
			
			animate();
		}
	}
	
	function animate() {
		pupilaa();
		var seconds = ( Date.now() - startTime ) / 1000;
		
		context.clearRect( 0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT );

		for( var i = 0, len = eyes.length; i < len; i++ ) {
			var eye = eyes[i];
			
			if( seconds > eye.activationTime * DISPLAY_DURATION ) {
				eye.activate();
			};
			
			eye.update( mouse, wip );
		}
		
		requestAnimFrame( animate );
	}
	
  setTimeout( initialize, 1 );
  
})();


function Eye( canvas, x, y, scale, time ) {
	this.canvas = canvas;
	this.context = this.canvas.getContext( '2d' )

	this.activationTime = time;

	this.isf = 0.06935336207220613;
	
	this.irisSpeed = 0.01 + this.isf / scale;
	
	console.log(this.isf);

	this.blinkSpeed = 0.2 + ( Math.random() * 0.2 );
	this.blinkInterval = 5000 + 2000 * ( Math.random() );
	

	this.blinkTime = Date.now();
	
	this.scale = scale;
	this.size = 70 * scale;
	
	this.x = x * canvas.width; 
	this.y = y * canvas.height + ( this.size * 0.15 );
	
	this.iris = {
		x: this.x,
		y: this.y - ( this.size * 0.1 ),
		size: this.size * 0.2
	};
	
	this.pupil = {
		width: 2 * scale,
		height: this.iris.size * 0.75
	};
	
	this.exposure = {
		top: 0.1 + ( Math.random() * 0.3 ),
		bottom: 0.5 + ( Math.random() * 0.3 ),
		current: 0,
		target: 1
	};
	

	this.tiredness = ( 0.5 - this.exposure.top ) + 0.1;
	
	this.isActive = false;
	
	this.activate = function() {
		this.isActive = true;
	}
	
	this.update = function( mouse, wip ) {
		if( this.isActive === true ) {
			this.render( mouse, wip );
		}
	}
	
	this.render = function( mouse, wip ) {
		var time = Date.now();
		
		if( this.exposure.current < 0.012 ) {
			this.exposure.target = 1;
		}
		else if( time - this.blinkTime > this.blinkInterval ) {
			this.exposure.target = 0;
			this.blinkTime = time;
		}
		
		canvas.width = window.innerWidth;
		canvas.height = window.innerHeight;
		
		this.exposure.current += ( this.exposure.target - this.exposure.current ) * this.blinkSpeed;
		
		var el = { x: this.x - ( this.size * 0.8 ), y: this.y - ( this.size * 0.1 ) };
		var er = { x: this.x + ( this.size * 0.8 ), y: this.y - ( this.size * 0.1 ) };

		var et = { x: this.x, y: this.y - ( this.size * ( 0.5 + ( this.exposure.top * this.exposure.current ) ) ) };
		var eb = { x: this.x, y: this.y - ( this.size * ( 0.5 - ( this.exposure.bottom * this.exposure.current ) ) ) };

		var eit = { x: this.x, y: this.y - ( this.size * ( 0.5 + ( ( 0.5 - this.tiredness ) * this.exposure.current ) ) ) };

		var ei = { x: this.x, y: this.y - ( this.iris.size ) };

		var eio = { 
			x: ( mouse.x - ei.x ) / ( window.innerWidth - ei.x ), 
			y: ( mouse.y ) / ( window.innerHeight )
		};

		ei.x += eio.x * 16 * Math.max( 1, this.scale * 0.4 );
		ei.y += eio.y * 10 * Math.max( 1, this.scale * 0.4 );
		
		this.iris.x += ( ei.x - this.iris.x ) * this.irisSpeed;
		this.iris.y += ( ei.y - this.iris.y ) * this.irisSpeed;
		
		// Ochi
		this.context.fillStyle = 'rgba(255,255,255,1.0)';
		this.context.strokeStyle = 'rgba(100,100,100,1.0)';
		this.context.beginPath();
		this.context.lineWidth = 3;
		this.context.lineJoin = 'round';
		this.context.moveTo( el.x, el.y );
		this.context.quadraticCurveTo( et.x, et.y, er.x, er.y );
		this.context.quadraticCurveTo( eb.x, eb.y, el.x, el.y );
		this.context.closePath();
		this.context.stroke();
		this.context.fill();
		
		// Irisul1
		this.context.save();
		this.context.globalCompositeOperation = 'source-atop';
		this.context.translate(this.iris.x*0.1,0);
		this.context.scale(0.9,1);
		this.context.strokeStyle = 'rgba(0,0,0,0.5)';
		this.context.fillStyle = 'rgba(130,50,90,0.9)';
		this.context.lineWidth = 2;
		this.context.beginPath();
		this.context.arc(this.iris.x, this.iris.y, this.iris.size, 0, Math.PI*2, true);
		this.context.fill();
		this.context.stroke();
		this.context.restore();
		
		// Irisul2
		this.context.save();
		this.context.shadowColor = 'rgba(255,255,255,0.5)';
		this.context.shadowOffsetX = 0;
		this.context.shadowOffsetY = 0;
		this.context.shadowBlur = 2 * this.scale;
		this.context.globalCompositeOperation = 'source-atop';
		this.context.translate(this.iris.x*0.1,0);
		this.context.scale(0.9,1);
		this.context.fillStyle = 'rgba(255,255,255,0.2)';
		this.context.beginPath();
		this.context.arc(this.iris.x, this.iris.y, this.iris.size * 0.7, 0, Math.PI*2, true);
		this.context.fill();
		this.context.restore();
		
		// Pupila
		this.context.save();
		this.context.globalCompositeOperation = 'source-atop';
		this.context.fillStyle = 'rgba(0,0,0,0.9)';
		this.context.beginPath();
		this.context.moveTo( this.iris.x, this.iris.y - ( this.pupil.height * 0.5 ) );
		this.context.quadraticCurveTo( this.iris.x + ( this.pupil.width * wip.w ), this.iris.y, this.iris.x, this.iris.y + ( this.pupil.height * 0.5 ) );
		this.context.quadraticCurveTo( this.iris.x - ( this.pupil.width * wip.w ), this.iris.y, this.iris.x, this.iris.y - ( this.pupil.height * 0.5 ) );
		this.context.fill();
		this.context.restore();
		
		this.context.save();
		this.context.shadowColor = 'rgba(0,0,0,0.9)';
		this.context.shadowOffsetX = 0;
		this.context.shadowOffsetY = 0;
		this.context.shadowBlur = 10;
		
		// Umbra sus
		this.context.fillStyle = 'rgba(120,120,120,0.2)';
		this.context.beginPath();
		this.context.moveTo( el.x, el.y );
		this.context.quadraticCurveTo( et.x, et.y, er.x, er.y );
		this.context.quadraticCurveTo( eit.x, eit.y, el.x, el.y );
		this.context.closePath();
		this.context.fill();
		
		this.context.restore();
		
	}
}

window.requestAnimFrame = (function(){
  return  window.requestAnimationFrame       || 
          window.webkitRequestAnimationFrame || 
          window.mozRequestAnimationFrame    || 
          window.oRequestAnimationFrame      || 
          window.msRequestAnimationFrame     || 
          function(/* function */ callback, /* DOMElement */ element){
            window.setTimeout(callback, 1000 / 60);
          };
})();