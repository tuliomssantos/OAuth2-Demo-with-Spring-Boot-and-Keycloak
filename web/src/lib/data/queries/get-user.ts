'use server'

import { cookies } from 'next/headers'

// import { User } from '@/lib/types/user'

export const getUser = async () => {
  const cookieStore = cookies()
  const JSSESSION = cookieStore.get('JSESSIONID')

  try {
    const userInfoResponse = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/api/auth/me`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Cookie: `JSESSIONID=${JSSESSION?.value}`,
        },
        credentials: 'include',
        cache: 'no-store',
      }
    )

    if (!userInfoResponse.ok) {
      return null
    }

    return await userInfoResponse?.json()
  } catch (error) {
    console.log(error)
    return null
  }
}
