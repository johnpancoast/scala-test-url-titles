const urlTitleStateInit = 'init';
const urlTitleStateValid = 'valid';
const urlTitleStateUpdating = 'updating';
const urlTitleStateEmpty = 'empty';
const urlTitleStateError = 'error';

/**
 * Vue component
 *
 * For the purposes of this test app, we just wrap the entire #app element because
 * it's a small app, but obviously for larger apps we'd have separate components.
 */
var app = new Vue({
    el: '#app',
    data: {
        url: '',
        urlTitle: '',
        urlTitleState: urlTitleStateInit
    },
    computed: {
        /**
         * Get the CSS classes for the title element based on the title's state
         *
         * @see https://vuejs.org/v2/guide/class-and-style.html
         *
         * @returns Object
         */
        getUrlTitleStateClasses: function () {
            return {
                'text-primary': this.urlTitleState == urlTitleStateValid,
                'font-weight-bold': this.urlTitleState == urlTitleStateValid || this.urlTitleState == urlTitleStateError,
                'font-italic': this.urlTitleState == urlTitleStateUpdating,
                'text-warning': this.urlTitleState == urlTitleStateEmpty,
                'text-danger': this.urlTitleState == urlTitleStateError
            };
        }
    },
    watch: {
        /**
         * Watch for changes to url input
         *
         * @param val
         * @param oldVal
         */
        url: function (val, oldVal) {
            if (val == oldVal) {
                return;
            }

            this.initUrlTitle();
        }
    },
    methods: {
        /**
         * Init url element
         */
        initUrl: function () {
            this.url = '';
            this.initUrlTitle();
        },

        /**
         * Initialize url title
         */
        initUrlTitle: function () {
            this.urlTitleState = urlTitleStateInit;
            this.urlTitle = '';
        },

        /**
         * Is the url property valid
         *
         * @returns {boolean}
         */
        isUrlValid: function () {
            // TODO Consider possibilities besides refs.
            // I've just seen this method used often so, if it works...
            return this.$refs.url.validity.valid;
        },

        /**
         * Update url title
         *
         * @param event The event that spawned the call to this method
         */
        updateUrlTitle: function (event) {
            this.urlTitleState = urlTitleStateUpdating;
            this.urlTitle = 'Searching...';

            // Do nothing if our url field isn't valid.
            // Let layout handle the display errors since this method is only concerned with sending a valid URL to
            // endpoint.
            if (!this.isUrlValid()) {
                return;
            }

            axios
                .get('/url-title', {
                    params: {
                        url: encodeURI(this.url)
                    }
                })
                .then(response => {
                    var title = response.data;

                    if (title == '') {
                        this.urlTitleState = urlTitleStateEmpty;
                        this.urlTitle = 'No Title Found';
                    } else {
                        this.urlTitleState = urlTitleStateValid;
                        this.urlTitle = title
                    }
                })
                .catch(error => {
                    var errorString = error.response.statusText;

                    this.urlTitleState = urlTitleStateError;
                    this.urlTitle = errorString
                    console.log(error)
                })
        }
    }
})
