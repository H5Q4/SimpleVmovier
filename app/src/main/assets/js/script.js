(function() {
  'use strict';

  $('.other-video,.other-video2').off('click');
  $('.other-video,.other-video2').on('click', (event)=>{
    const $this = $(event.currentTarget);
    const index = $this.data('index', 0);
    window.android.onClickMovieInBackstage(index);
  });

  $('.new-view-link').off('click');
  $('.new-view-link').on('click', (event)=> {
    const $this = $(event.currentTarget);
    const id = $this.data('id');
    window.android.onClickRecommendMovie(id);
  });

  const links = document.getElementsByTagName('a');
  for (var i = 0; i < links.length; i++) {
    const link = links.item(i);
    disableLink(link);
  }

  // Disable the functionality of tag <a>.
  function disableLink(link) {
    link.style.color = '#000';
    link.removeAttribute('href');
    link.setAttribute('disabled', 'disabled');
  }

})();
