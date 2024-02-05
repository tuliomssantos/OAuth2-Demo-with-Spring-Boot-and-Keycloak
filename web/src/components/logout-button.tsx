'use client'

import { useRouter } from 'next/navigation'
import { Button } from '@/components/ui/button'

export default function LogoutButton() {
  const router = useRouter()

  const handleLogout = async () => {
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/api/auth/logout`,
      {
        method: 'GET',
        credentials: 'include',
      }
    )

    if (!res.ok) {
      console.log('error')
      console.log(res.status)
      console.log(res.statusText)
      console.log(res.headers)
      console.log(res.body)
    }

    if (res.redirected) {
      console.log('redirected')
      window.location.href = res.url
    }

    router.refresh()
  }

  return (
    <Button variant="outline" className="w-full" onClick={handleLogout}>
      Logout
    </Button>
  )
}
