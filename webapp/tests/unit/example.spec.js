import {
  shallowMount
} from '@vue/test-utils'
import MovieVue from '@/components/MovieVue.vue'

describe('MovieVue.vue', () => {
  it('renders props.msg when passed', () => {
    const msg = 'new message'
    const wrapper = shallowMount(MovieVue, {
      propsData: {
        msg
      }
    })
    expect(wrapper.text()).toMatch(msg)
  })
})
