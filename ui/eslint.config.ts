import antfu from '@antfu/eslint-config'

export default antfu(
  {
    stylistic: {
      indent: 2,
      quotes: 'single',
      semi: false,
      jsx: true,
    },
    formatters: true,
    unocss: true,
    vue: true,
    typescript: {
      tsconfigPath: './tsconfig.json',
    },
    ignores: ['**/dist/**', 'node_modules/**'],
    rules: {
      'vue/block-order': ['error', {
        order: ['template', 'script', 'style'],
      }],
      'style/brace-style': ['error', '1tbs', {allowSingleLine: true}],
      'no-undef': 'off',
      'unused-imports/no-unused-vars': 'off',
      'no-unused-vars': ['warn', {
        args: 'none',
      }],
    },
  }, {
    files: ['**/*.ts'],
    rules: {
      'ts/consistent-type-definitions': ['error', 'interface'],
    },
  },
)
