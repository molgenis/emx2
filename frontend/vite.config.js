import {defineConfig} from 'vite'
import emx2Package from './package.json'
import path from 'path'
import vue from '@vitejs/plugin-vue'

process.env.EMX2_VERSION = emx2Package.version

export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: [
      {
        find: '@',
        replacement: path.resolve(__dirname, './src'),
      },
    ],
  },
  server: {
    proxy: {
      '/api': 'http://localhost:8080',
      '/graphql': 'http://localhost:8080/api',
      // eslint-disable-next-line no-useless-escape
      '^/.*/tables/graphql': {
        target: 'http://localhost:8080',
      },
    },
  },

})

